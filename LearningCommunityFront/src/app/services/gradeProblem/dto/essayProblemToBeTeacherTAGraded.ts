export class EssayProblemToBeTeacherTAGradedDTO {
  constructor(public essayProblemToBeTeacherTAGradedID: string,
              public title: string,
              public maxPoint: number,
              public answer: string,
              public answererUsername: string,
              public referenceAnswer: string,
              public courseCode: string,
              public date: Date) {
  }


}
