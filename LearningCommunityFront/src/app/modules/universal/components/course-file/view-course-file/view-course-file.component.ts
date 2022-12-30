import {Component, Input, OnInit} from '@angular/core';
import {CourseFileDTO} from "../../../../../services/file/dto/CourseFileDTO";
import {NzTableQueryParams} from "ng-zorro-antd/table";
import {FileService} from "../../../../../services/file/file.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {CommonService} from "../../../../../services/common/common.service";
import {NzModalService} from "ng-zorro-antd/modal";

@Component({
  selector: 'app-view-course-file',
  templateUrl: './view-course-file.component.html',
  styleUrls: ['./view-course-file.component.css']
})
export class ViewCourseFileComponent implements OnInit {

  @Input()
  public courseCode: string = ''

  public files: CourseFileDTO[] = []

  public loading: boolean = false;

  public totalPage: number = 1
  public totalElement: number = 0

  public pageSize: number = 10
  public pageNum: number = 1

  //预览用 kkfileviewer 端口
  private previewPort: number = 8012


  constructor(private fileService: FileService,
              private message: NzMessageService,
              private modal: NzModalService,
              public commonService: CommonService) {
  }

  ngOnInit(): void {
    this.listCourseFiles()
  }

  public onQueryParamsChange(params: NzTableQueryParams) {
    const {pageSize, pageIndex, sort, filter} = params;
    this.pageNum = pageIndex;
    this.pageSize = pageSize;
    this.listCourseFiles()
  }

  public listCourseFiles() {
    this.loading = true
    this.fileService.listCourseFiles(
      this.courseCode,
      this.pageNum,
      this.pageSize,
      httpResponse => {
        this.loading = false
        this.totalPage = httpResponse.body?.totalPage as number;
        this.totalElement = httpResponse.body?.totalElement as number;
        this.files = httpResponse.body?.data as CourseFileDTO[]
      },
      httpErrorResponse => {
        this.loading = false
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }

  public onPreview(fileName: string) {
    //计算出fileUrl，预览的时候fileUrl应该是http://开头的绝对路径
    //由于window.open无法指定头部，因此只能在QueryString里带Authorization信息
    let fileUrl = this.commonService.getBackendUrlPrefix() + `/${this.courseCode}/files/${encodeURIComponent(fileName)}?Authorization=${this.commonService.getJwt()}`
    if (!fileUrl.startsWith("http")) {
      fileUrl = `http://host.docker.internal/` + fileUrl
    }

    //打开previewUrl，对应的端口上运行了kkfileviewer，就能获取到文件
    let previewUrl = `http://${document.domain}:${this.previewPort}/onlinePreview?url=${encodeURIComponent(fileUrl)}`
    window.open(previewUrl)
  }

  public onDownload(fileName: string) {
    //由于window.open无法指定头部，因此只能在QueryString里带Authorization信息
    let fileUrl = this.commonService.getBackendUrlPrefix() + `/${this.courseCode}/files/${encodeURIComponent(fileName)}?Authorization=${this.commonService.getJwt()}`
    window.open(fileUrl)
  }

  public onDelete(fileName: string) {
    let nzOnOk = () => {
      this.fileService.deleteFile(
        this.courseCode,
        fileName,
        httpResponse => {
          this.message.success(httpResponse.body?.message as string)
          this.listCourseFiles()
        },
        httpErrorResponse => {
          this.message.error(JSON.stringify(httpErrorResponse.error))
        }
      )
    }
    this.modal.confirm({
      nzTitle: "你确定吗",
      nzContent: `删除文件${fileName}后将永远无法找回`,
      nzOnOk: nzOnOk
    })
  }
}
