*****************************************************************************************
THE PKU SYSTEM: ONLINE UTHM HEALTH CENTER APPOINTMENT SYSTEM
*****************************************************************************************

In this section, a step by step guide is provided for the user to setup their 
computer device in order to use the system successfully.

-----------------------------------------------------------------------------------------
1. Download Android Studio
-----------------------------------------------------------------------------------------
Step 1: Download Adnroid Studio from website https://developer.android.com/studio \
Step 2: Install it in the computer.

-----------------------------------------------------------------------------------------
2. Setting up Firebase in Android Studio
-----------------------------------------------------------------------------------------
Step 1: Download the file folder 'thepku' and paste it in computer 
	directory C:\Users\User\AndroidStudioProjects \
Step 2: Open the file folder in Android Studio.\
Step 3: Selects Tools in menu bar and click on Firebase to open Firebase Assistant.\
Step 4: In the Assistant pane, selects Cloud Firestore.\
Step 5: Clicks on Connect to Firebase button and it will brings you to Firebase website.\
Step 6: Login with Google Account in the Firebase website.\
Step 7: Go back to Android Studio.\
Step 8: Click on add the Cloud Firestore SDK to your app button.\
Step 9: Click on Accept Changes.\
Step 10: Done.

-----------------------------------------------------------------------------------------
3. Configure access in Firebase website
-----------------------------------------------------------------------------------------
Step 1: Login with Google Account in the Firebase website\
Step 2: Click on Go to console\
Step 3: Select Add project\
Step 4: Enter project name 'thepku' and click on continue\
Step 5: Click on Create project\
Step 6: Open the project created\
Step 7: Select Android logo in main page\
Step 8: For Android package name, open gradle scripts in Android Studio and select the second build.gradle.\
Step 9: Copy the applicationId and paste it in Android package name column\
Step 10: Enter 'thepku' in app nickname column\
Step 11: Copy the SHA1 value from Android Studio and paste it in the last column\
Step 12: Click on register app button\
Step 13: Download google-services.json\
Step 14: Paste the json file in Android Studio FirebaseConnection\app \
Step 15: Click on continue to console button\
Step 16: Select firestore database and create database in project 'thepku'\
Step 17: Select start in production move and click on enable button\
Step 18: Go the rule and change false to true and click on publish\
Step 19: Create new collection in firestore database\
Collection ID: admin\
Document Id: Admin123\
Field: id	Type: String	Value: Admin123\
Field: name	Type: String	Value: Admin\
Field: password	Type: String	Value: 123456\
Step 20: Done

***********************************************************************************************

-> Briefing about the system functionalities

For admin side:
1. Appointment: manage approved appointment information
2. Request: manage appointment request
3. Report: generate report
4. Profile: Admin profile details

For student side:
1. Registration: register new student account
2. Appointment: manage approved appointment information
3. Booking: book appointment
4. Notification: view notification list
5. Profile: Student profile details

