export class EssayProblemsToBePeerGradedDTO {
  constructor(public essayProblemToBePeerGradedID: string,
              public title: string,
              public maxPoint: number,
              public answer: string,
              public answererUsername: string,
              public referenceAnswer: string,
              public courseCode: string,
              public gradedPointList: number[],
              public date: Date) {
  }


}
