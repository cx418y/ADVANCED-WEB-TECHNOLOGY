export class DetailedVersion {
  constructor(public homeworkVersionID: string,
              public homeworkID: string,
              public fromUsername: string,
              public createdTime: Date,
              public expireTime: Date,
              public content: string) {
  }
}
