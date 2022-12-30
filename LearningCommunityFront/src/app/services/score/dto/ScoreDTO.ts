export class ScoreDTO{
    constructor(
        public username: string,
        public realname: string,
        public gottenPoint: number,
        public maxPoint: number,
        public finishedHomeworkCnt: number,
        public totalHomeworkCnt: number,
        public score: number
    ){

    }
}