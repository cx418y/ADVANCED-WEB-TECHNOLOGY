/**
 需要登录才能访问的组件
 * 比如TeacherHomeComponent继承这个组件后，只要在自己的constructor中调用这个类的constructor，就可以实现把没有登陆的用户赶到登陆页面
 */
import {Router} from "@angular/router";
import {CommonService} from "../services/common/common.service";

export abstract class LoginRequiredComponent {

  private loginRequiredOn: boolean = false;

  protected constructor(private _router: Router,
                        private _commonService: CommonService) {
    if (this.loginRequiredOn && this._commonService.getJwt().length === 0) {
      this._router.navigate(['universal', 'userLogin'])
    }
  }

}
