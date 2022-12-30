export class DetailedHomework {

  public constructor(public homeworkID: string,
                     public courseCode: string,
                     public sectionID: string,
                     public title: string,
                     public details: string,
                     public oldMax: number,
                     public oldTTL: number) {
  }


}
