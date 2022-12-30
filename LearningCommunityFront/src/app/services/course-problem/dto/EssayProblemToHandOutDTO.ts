/**
 * 待分发的论述题
 */
export class EssayProblemToHandOutDTO {

  constructor(public essayProblemID: string,
              public peerGrading: boolean,
              public point: number) {
  }
}
