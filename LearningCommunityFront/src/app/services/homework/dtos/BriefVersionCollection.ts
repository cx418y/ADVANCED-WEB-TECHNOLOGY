import {BriefVersion} from "./BriefVersion";

export class BriefVersionCollection {

  constructor(public latestVersion: BriefVersion,
              public oldVersions: BriefVersion[]) {
  }

}
