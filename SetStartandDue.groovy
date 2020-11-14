  
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import static java.util.Calendar.*

def pattern = "yyyy-MM-dd"

//Define offset how many days you want to push issues forward
def offset = 5

def count = 10 //add here bit which counts the number of issues in the project

def i = int
for (i = 6; i <8; i++) {
   


def issueKey = ("IMPO3-"+i)

def thisIssue = get("/rest/api/2/issue/${issueKey}").asObject(Map).body
def thisIssueFields = thisIssue.find {it.key == "fields"} ?.value as Map
def thisIssueLinks = thisIssueFields.find {it.key == "issuelinks"} ?.value as Map


//Make skip if not epic part    

//Make skip closed projects part        
//If (thisIssueFields.get("status")== "Done"){continue} 
        


println(issueKey)


println("StartDate aka customfield_10015")
println(thisIssueFields.get("customfield_10015"))
println("DueDate")
println(thisIssueFields.get("duedate"))

//def oldDueDate = thisIssueFields.get("duedate")
String oldStartDate = thisIssueFields.get("customfield_10015")
def intermediateStartDate = new Date().parse(pattern, oldStartDate)


//Make if status is not started, move also start date

def startSetter = put("/rest/api/2/issue/${issueKey}")
        .header('Content-Type', 'application/json')
        .body([
            fields:[
                // Set the due date to today's date
                customfield_10015: (intermediateStartDate+offset).format('yyyy-MM-dd') as String
            ]
        ])
        .asString()


String oldDueDate = thisIssueFields.get("duedate")
def intermediateDueDate = new Date().parse(pattern, oldDueDate)



def dueSetter = put("/rest/api/2/issue/${issueKey}")
        .header('Content-Type', 'application/json')
        .body([
            fields:[
                // Set the due date to today's date
                duedate: (intermediateDueDate+offset).format('yyyy-MM-dd') as String
            ]
        ])
        .asString()
        

println(thisIssueFields)
}

//statuscategorychangedate:2020-11-11T17:28:07.258+0000, customfield_10110:null, fixVersions:[], customfield_10111:null, customfield_10079:null, resolution:null, customfield_10112:null, customfield_10113:null, customfield_10114:null, customfield_10104:null, customfield_10105:null, customfield_10109:null, lastViewed:2020-11-14T18:48:02.496+0000, customfield_10181:null, priority:[self:https://iceye-test.atlassian.net/rest/api/2/priority/3, iconUrl:https://iceye-test.atlassian.net/images/icons/priorities/medium.svg, name:Medium, id:3], customfield_10100:null, customfield_10101:null, customfield_10102:null, labels:[], customfield_10103:null, timeestimate:null, aggregatetimeoriginalestimate:null, versions:[], issuelinks:[[id:10662, self:https://iceye-test.atlassian.net/rest/api/2/issueLink/10662, type:[id:10000, name:Blocks, inward:is blocked by, outward:blocks, self:https://iceye-test.atlassian.net/rest/api/2/issueLinkType/10000], outwardIssue:[id:10619, key:PSIT-38, self:https://iceye-test.atlassian.net/rest/api/2/issue/10619, fields:[summary:System Level Check Group 4, status:[self:https://iceye-test.atlassian.net/rest/api/2/status/10091, description:, iconUrl:https://iceye-test.atlassian.net/, name:To Do, id:10091, statusCategory:[self:https://iceye-test.atlassian.net/rest/api/2/statuscategory/2, id:2, key:new, colorName:blue-gray, name:To Do]], priority:[self:https://iceye-test.atlassian.net/rest/api/2/priority/3, iconUrl:https://iceye-test.atlassian.net/images/icons/priorities/medium.svg, name:Medium, id:3], issuetype:[self:https://iceye-test.atlassian.net/rest/api/2/issuetype/10076, id:10076, description:A collection of related bugs, stories, and tasks., iconUrl:https://iceye-test.atlassian.net/secure/viewavatar?size=medium&avatarId=10307&avatarType=issuetype, name:Epic, subtask:false, avatarId:10307, entityId:5511d129-509d-4596-be70-e415b60c148d]]]]], assignee:null, status:[self:https://iceye-test.atlassian.net/rest/api/2/status/10117, description:, iconUrl:https://iceye-test.atlassian.net/, name:To Do, id:10117, statusCategory:[self:https://iceye-test.atlassian.net/rest/api/2/statuscategory/2, id:2, key:new, colorName:blue-gray, name:To Do]], components:[], customfield_10175:null, customfield_10178:null, customfield_10179:null, aggregatetimeestimate:null, creator:[self:https://iceye-test.atlassian.net/rest/api/2/user?accountId=5f3a7facfdc3f5003f9cb843, accountId:5f3a7facfdc3f5003f9cb843, avatarUrls:[48x48:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 24x24:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 16x16:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 32x32:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png], displayName:Arttu Tiainen, active:true, timeZone:Europe/London, accountType:atlassian], subtasks:[], customfield_10160:null, customfield_10040:null, customfield_10161:null, customfield_10041:null, customfield_10162:null, customfield_10042:null, customfield_10163:null, reporter:[self:https://iceye-test.atlassian.net/rest/api/2/user?accountId=5f3a7facfdc3f5003f9cb843, accountId:5f3a7facfdc3f5003f9cb843, avatarUrls:[48x48:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 24x24:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 16x16:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png, 32x32:https://avatar-management--avatars.us-west-2.prod.public.atl-paas.net/initials/AT-4.png], displayName:Arttu Tiainen, active:true, timeZone:Europe/London, accountType:atlassian], customfield_10043:null, customfield_10164:null, customfield_10044:null, aggregateprogress:[progress:0, total:0], customfield_10165:null, customfield_10166:null, customfield_10167:null, customfield_10168:null, customfield_10159:null, customfield_10038:null, customfield_10039:null, progress:[progress:0, total:0], votes:[self:https://iceye-test.atlassian.net/rest/api/2/issue/IMPO3-6/votes, votes:0, hasVoted:false], worklog:[startAt:0, maxResults:20, total:0, worklogs:[]], issuetype:[self:https://iceye-test.atlassian.net/rest/api/2/issuetype/10097, id:10097, description:A collection of related bugs, stories, and tasks., iconUrl:https://iceye-test.atlassian.net/secure/viewavatar?size=medium&avatarId=10307&avatarType=issuetype, name:Epic, subtask:false, avatarId:10307, entityId:650a74c2-5f80-4ede-ace0-d77bb59b2f79], timespent:null, customfield_10150:null, customfield_10151:null, project:[self:https://iceye-test.atlassian.net/rest/api/2/project/10045, id:10045, key:IMPO3, name:Impo3, projectTypeKey:software, simplified:true, avatarUrls:[48x48:https://iceye-test.atlassian.net/secure/projectavatar?pid=10045&avatarId=10409, 24x24:https://iceye-test.atlassian.net/secure/projectavatar?size=small&s=small&pid=10045&avatarId=10409, 16x16:https://iceye-test.atlassian.net/secure/projectavatar?size=xsmall&s=xsmall&pid=10045&avatarId=10409, 32x32:https://iceye-test.atlassian.net/secure/projectavatar?size=medium&s=medium&pid=10045&avatarId=10409]], customfield_10031:null, customfield_10152:null, customfield_10032:null, customfield_10153:null, customfield_10033:null, customfield_10154:null, aggregatetimespent:null, customfield_10034:null, customfield_10155:null, customfield_10035:null, customfield_10156:null, customfield_10157:null, customfield_10158:null, customfield_10037:[], customfield_10148:null, customfield_10149:null, resolutiondate:null, workratio:-1, issuerestriction:[issuerestrictions:[:], shouldDisplay:true], watches:[self:https://iceye-test.atlassian.net/rest/api/2/issue/IMPO3-6/watchers, watchCount:1, isWatching:true], created:2020-11-11T17:28:06.981+0000, customfield_10140:null, customfield_10020:null, customfield_10141:null, customfield_10142:null, customfield_10021:null, customfield_10143:null, customfield_10022:null, customfield_10144:null, customfield_10023:null, customfield_10145:null, customfield_10146:null, customfield_10147:null, customfield_10137:null, customfield_10016:null, customfield_10017:purple, customfield_10138:null, customfield_10139:null, customfield_10018:[hasEpicLinkFieldDependency:false, showField:false, nonEditableReason:[reason:PLUGIN_LICENSE_ERROR, message:The Parent Link is only available to Jira Premium users.]], customfield_10019:0|i005zz:, updated:2020-11-14T19:09:45.558+0000, customfield_10090:null, customfield_10091:null, customfield_10092:null, customfield_10093:null, customfield_10094:null, timeoriginalestimate:null, customfield_10095:null, customfield_10096:null, description:null, customfield_10130:null, customfield_10131:null, customfield_10098:null, customfield_10010:null, customfield_10132:null, customfield_10099:null, customfield_10133:null, customfield_10134:null, customfield_10014:null, timetracking:[:], customfield_10136:null, customfield_10015:2020-12-08, customfield_10005:null, customfield_10126:null, customfield_10006:null, customfield_10127:null, customfield_10128:null, security:null, customfield_10007:null, customfield_10008:null, customfield_10129:null, attachment:[], customfield_10009:null, summary:PCMA, customfield_10080:null, customfield_10082:null, customfield_10083:null, customfield_10085:null, customfield_10086:null, customfield_10120:null, customfield_10087:null, customfield_10000:{}, customfield_10121:null, customfield_10088:null, customfield_10089:null, customfield_10001:null, customfield_10122:null, customfield_10002:null, customfield_10123:null, customfield_10003:null, customfield_10124:null, customfield_10125:null, customfield_10004:null, customfield_10116:null, environment:null, customfield_10117:null, customfield_10119:null, duedate:2021-01-01, comment:[comments:[], maxResults:0, total:0, startAt:0]]
