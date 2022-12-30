import {Component, Input, OnInit} from '@angular/core';
import {EssayProblemFinishedDTO} from "../../../../services/finished-problem/dto/EssayProblemFinishedDTO";
import {FinishedProblemService} from "../../../../services/finished-problem/finished-problem.service";
import {NzMessageService} from "ng-zorro-antd/message";
import {NzTableQueryParams} from "ng-zorro-antd/table";
import {patchTsGetExpandoInitializer} from "@angular/compiler-cli/ngcc/src/packages/patch_ts_expando_initializer";

@Component({
  selector: 'app-answer-record',
  templateUrl: './answer-record.component.html',
  styleUrls: ['./answer-record.component.css']
})
export class AnswerRecordComponent implements OnInit {

  @Input()
  public courseCode: string = '';

  public pageNum: number = 1;
  public pageSize: number = 10;
  public totalPage: number = 1;
  public totalElement: number = 0;
  public essayProblemFinishedDTOList: EssayProblemFinishedDTO[] = []
  public loading: boolean = false;


  constructor(private finishedProblemService: FinishedProblemService,
              private message: NzMessageService) {
  }


  ngOnInit(): void {
    this.getEssayProblemFinished()
  }

  public onQueryParamsChange(params: NzTableQueryParams) {
    const {pageSize, pageIndex, sort, filter} = params;
    this.pageSize = pageSize;
    this.pageNum = pageIndex;
    this.getEssayProblemFinished();
  }

  public getEssayProblemFinished() {
    this.loading = true;
    this.finishedProblemService.getEssayProblemFinished(
      this.courseCode,
      this.pageNum,
      this.pageSize,
      httpResponse => {
        this.loading = false;
        this.totalPage = httpResponse.body?.totalPage as number;
        this.totalElement = httpResponse.body?.totalElement as number;
        this.essayProblemFinishedDTOList = httpResponse.body?.data as EssayProblemFinishedDTO[]
      },
      httpErrorResponse => {
        this.loading = false;
        this.message.error(JSON.stringify(httpErrorResponse.error))
      }
    )
  }


}
