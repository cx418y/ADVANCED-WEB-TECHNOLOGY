package org.adweb.learningcommunityback.business.homeworkmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllVersionBriefDTO {
    private VersionBriefDTO latestVersion;
    private List<VersionBriefDTO> oldVersions;
}
