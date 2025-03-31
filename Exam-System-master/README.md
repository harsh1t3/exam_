# EXAM_ - A Java-Based Exam System

**EXAM_** is an intuitive and cross-platform online exam system designed in Java. This project is developed to facilitate the smooth execution of various types of intra-organizational assessments.

The application requires a MySQL Server hosted on a Local Area Network (intranet). Once set up, the system can be installed on candidate devices, enabling students to log in with administrator-provided credentials and take their assessments seamlessly.

## Key Features

- Efficient exam scheduling and management
- Instant grading and result display
- Secure storage and retrieval of exam data
- Personalized feedback for candidates
- Comprehensive result analysis and report generation
- Data validation and security measures
- Visual representation of evaluation reports for instructors

## Required Java Dependencies

To ensure smooth functioning, the following external Java libraries are needed:

1. JDBC Connector - Establishes MySQL database connectivity
2. JFreeCharts - Enables visualization of exam results and reports

## Development Tools

The project has been built using VS Code.

### Running the Project
To execute the application, build and run the following file:
```
Exam System\Exam System\src\Login\User_Login.java
```

## System Modules

The project is divided into three main modules, catering to different types of users:

### Candidate Module
- Candidates can log in to attempt scheduled exams.
- They can review their previous exam scores and personal details.
- Provides access to upcoming exams relevant to their batch/group.
- Allows participation in mock/practice tests.
- Enables profile modification and password updates.

### Instructor Module
- Instructors can create, edit, and schedule exams for assigned batches.
- They can modify exam parameters such as date, time, and question limits.
- Facilitates detailed result analysis and visualization.
- Allows profile and credential management.

### Administrator Module
- Administrators have complete control over the exam system.
- They can add, remove, or assign instructors and students to groups.
- Provides access to detailed user and group information.
- Allows modification of system data, including exam details and user accounts.

## Screenshots
Project screenshots can be found in the `Screenshots` directory within this repository.

## Future Enhancements

There is scope for further improvement, including:
- Enhancing the overall UI for a better user experience
- Implementing automated email notifications for exam results
- Enabling automatic evaluation of programming-based and subjective answers
- Improving data visualization and analysis features
- Introducing video monitoring for student verification and plagiarism detection
- Enhancing password recovery methods using email/OTP authentication
- Strengthening data validation and security protocols
- Integrating CAPTCHA verification to prevent system misuse

This project was developed by:
- Harshit Kumar
- Harshdeep Singh
- Chandan Kumar

Students of the Indian Institute of Information Technology, **4th Semester (2024-25)**.