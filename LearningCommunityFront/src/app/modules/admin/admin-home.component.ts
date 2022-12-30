import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {CommonService} from "../../services/common/common.service";

@Component({
  selector: 'app-admin',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  constructor(private router: Router,
              private commonService: CommonService) {
  }

  ngOnInit(): void {
  }


  public logout(): void {
    this.commonService.deleteJwt();
    this.router.navigate(['universal', 'adminLogin'])
  }
}
