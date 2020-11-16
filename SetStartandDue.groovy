def pattern = "yyyy-MM-dd" //Time format

//Define offset how many days you want to push issues forward
int offset = 5

int i = 55 //The first issue number


//Need to make a issue counter to prevent going over 
int count = 58 //The last issue number

String projectName= "IMPO3"


for (i ; i <count+1; i++) {
   


def issueKey = (projectName+"-"+i)

println(issueKey)
def thisIssue = get("/rest/api/2/issue/${issueKey}").asObject(Map).body


//Need to make a non-issue skipper
//if(thisIssue==null){
//   println("non-issue, skipping the number")
//    continue
//}

def thisIssueFields = thisIssue.find {it.key == "fields"} ?.value as Map

def thisIssueLinks = thisIssueFields.find {it.key == "issuelinks"} ?.value as Map


//We get the issue type

String[] issuetype = thisIssueFields.get("issuetype")
println(issuetype[4])
//If issue type is not epic, rest of the loop is skipped
if(issuetype[4]!="name=Epic"){
   println("Issue not epic, no need to push")
    continue
}

//We get the issue status
String[] issueStatus = thisIssueFields.get("status")

println(issueStatus[3])

//If the issue status is done, issue is not pushed
if(issueStatus[3]=="name=Done"){
   println("Issue done, no need to push")
    continue
}




String oldStartDate = thisIssueFields.get("customfield_10015")
def intermediateStartDate = new Date().parse(pattern, oldStartDate)


//If the issue has not been started, the start date is pushed as well
if(issueStatus[3]=="name=TO DO"){
      println("Issue not started, the start date is pushed")
   
    put("/rest/api/2/issue/${issueKey}")
        .header('Content-Type', 'application/json')
        .body([
            fields:[
                // Set the due date to today's date
                customfield_10015: (intermediateStartDate+offset).format('yyyy-MM-dd') as String
            ]
        ]).asString()
        
        //println(startSetter)
}


String oldDueDate = thisIssueFields.get("duedate")


//If there is no old Due Date, it will be set as the start date +1. If the due date exists, it will be pushed by offset
if(oldDueDate==null){
    println("there is no old Due Date, it will be set as the start date +1")
    put("/rest/api/2/issue/${issueKey}")
        .header('Content-Type', 'application/json')
        .body([
            fields:[
                // Set the due date to today's date
                duedate: (intermediateStartDate+offset+1).format('yyyy-MM-dd') as String
            ]
        ])
        .asString()
} else {
    //If there is a due date previously, it is defined as a date and then moved by the offset.
    def intermediateDueDate = new Date().parse(pattern, oldDueDate)
    println("Due date pushed")
    put("/rest/api/2/issue/${issueKey}")
        .header('Content-Type', 'application/json')
        .body([
            fields:[
                // Set the due date to today's date
                duedate: (intermediateDueDate+offset).format('yyyy-MM-dd') as String
            ]
        ])
        .asString()
        }

}