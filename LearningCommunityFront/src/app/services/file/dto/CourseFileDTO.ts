export class CourseFileDTO {
  public constructor(public fileName: string,
                     public description: string,
                     public courseCode: string,
                     public fromUsername: string,
                     public fileSize: number) {
  }
}
