export class EssayProblemFinishedDTO {
  constructor(public essayProblemFinishedID: string,
              public title: string,
              public maxPoint: number,
              public gottenPoint: number,
              public answer: string,
              public referenceAnswer: string,
              public answererUsername: string,
              public courseCode: string,
              public date: Date) {
  }
}
