export class EssayProblemToBeDoneDTO {
  constructor(public essayProblemToBeDoneID: string,
              public title: string,
              public point: number,
              public courseCode: string,
              public peerGrading: boolean,
              public date: Date) {
  }


}
