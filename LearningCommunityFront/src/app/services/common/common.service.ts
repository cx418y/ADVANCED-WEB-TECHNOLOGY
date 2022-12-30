import {Injectable} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {NavigationExtras, Router} from "@angular/router";
import {NzUploadFile} from "ng-zorro-antd/upload";
import jwt_decode from "jwt-decode";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  private mockBackendUrlPrefix: string = "http://127.0.0.1:4523/mock/789114"
  private realBackendUrlPrefix: string = "backAPI"

  constructor() {
  }


  public tinyMceKey(): string {
    return 'eb8j0449f1c27uzxvgd500djbxmbe1buv8ms8mf4bcivm7qh'
  }

  public getBackendUrlPrefix(): string {
    return this.realBackendUrlPrefix;
  }

  public getUsernameByJwt(): string {
    let jwt: string = this.getJwt();
    if (!jwt) {
      return 'Unknown User'
    }
    //解码jwt
    let payload = jwt_decode(jwt) as any
    return payload['username']
  }

  public setJwt(jwt: string): void {
    sessionStorage.setItem('jwt', jwt);
  }

  public getJwt(): string {
    return sessionStorage.getItem('jwt') != null ? sessionStorage.getItem('jwt') as string : '';
  }

  public deleteJwt(): void {
    sessionStorage.removeItem('jwt');
  }

  public formValidation(validateForm: FormGroup) {
    if (!validateForm.valid) {
      Object.values(validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({onlySelf: true});
        }
      });
      throw new Error('表单验证失败');
    }
  }


  /**
   * 当url相同但是参数不同的时候，直接navigateByUrl不会重新加载component
   * 使用这个函数可以强制每次navigate都重新加载component
   * @param router
   * @param destUrl
   * @param queryParams
   */
  public safeNavigateByUrl(router: Router, destUrl: string, queryParams: object) {
    router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      let navigationExtras: NavigationExtras = {
        queryParams: queryParams,
      }
      console.log(navigationExtras)
      router.navigate([destUrl], navigationExtras);
    })
  }


}
