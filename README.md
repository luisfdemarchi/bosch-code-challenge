# Doctor's appointment service

Service responsible for managing doctor's appointments

## How do I run locally

`./gradlew bootRun` will start the project locally at http://localhost:8080

##Endpoints

The  service contains 2 endpoints as detailed as below. 
It only accepts valid IDs which are mocked in the application.

Valid doctor IDs: from DOC-001 to DOC-019

Valid patient IDs: from PATIENT-001 to PATIENT-009

###GET

http://localhost:8080/v1/appointments?doctorId=DOC-009
will return  the list of appointments of a doctor

http://localhost:8080/v1/appointments?patientId=PATIENT-001
will return  the list of appointments of a patient

###POST

Will create an appointment for a patient in an specific time as the example below.
If doctor or patient already has an appointment at this time, an error is thrown

http://localhost:8080/v1/appointments

Body:
```aidl
{
    "doctorId": "DOC-003",
    "patientId": "PATIENT-002",
    "scheduledDateTime": "14:00"
}
```