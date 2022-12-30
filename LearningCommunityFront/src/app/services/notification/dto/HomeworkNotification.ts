export class HomeworkNotification {
  constructor(public homeworkNotificationID: string,
              public courseCode: string,
              public username: string,
              public type: string,
              public message: string,
              public homeworkID: string,
              public homeworkTitle: string) {
  }

}
