# JFit Java Project

Created by: [Jake Phelan](https://github.com/jakephelan1)

https://github.com/user-attachments/assets/89cf3d2f-b9d5-4fbe-bf0c-f4b3adf4c5cc

## Project Description
JFit is a Java-based fitness application that helps users manage their workout routines, track their calorie and protein intake, and monitor their fitness progress. The application provides personalized workout plans based on the user's preferences and fitness goals.

## Features
- User account creation and login system
- Personalized workout plans based on user's fitness level and goals
- Daily workout schedule with exercises, sets, and reps
- Calorie and protein intake tracking
- Body measurements tracking (weight, height, etc.)
- Customizable workout frequency (3-7 days per week)
- Progress tracking and goal setting
- User profile management and updates

## Tools Used
- **Java:** Primary programming language
- **JavaFX:** GUI framework for creating the desktop application
- **FXML:** XML-based user interface markup language for JavaFX
- **CSS:** Styling for the JavaFX UI
- **File I/O:** For reading workout data from CSV files and storing user data
- **Jackson:** JSON processing for API interactions (nutrition data)

## Setup and Installation
1. **Clone the Repository**
   ```bash
   git clone https://github.com/jakephelan1/JFit.java.git
   cd JFit.java
   ```

2. **Install Dependencies**
   - Ensure you have Java JDK 11 or later installed
   - Make sure JavaFX is set up in your development environment

3. **Set Up the Project**
   - Open the project in your preferred Java IDE (e.g., IntelliJ IDEA, Eclipse)
   - Configure the project to use JavaFX

4. **Run the Application**
   - Run the `App.java` file to start the application

## Usage
1. **Launch the Application:** Run the `App.java` file
2. **Create an Account or Login:** Use the login screen to create a new account or log in
3. **Set Up Your Profile:** Enter your personal details, fitness goals, and preferences
4. **View Your Workout Plan:** Navigate through the daily workout plans
5. **Track Your Progress:** Enter the food you eat throughout the day to keep track of your calorie and protein intake.
6. **Update Your Information:** Modify your profile information as needed

## Project Structure
- `src/main/java/application/jf/`
  - `App.java`: Main application file
  - `ButtonsCont.java`: Controller for main buttons
  - `Controller.java`: Main controller for user data and navigation
  - `DayCont.java`: Controller for daily workout view
  - `Login.java`: Handles user login and account creation
  - `UpdateInfo.java`: Manages user profile updates
  - `UserData.java`: User data model
- `src/main/resources/`
  - `CSVs/`: Contains workout plan CSV files
  - `application/jf/`: FXML and CSS files for UI

## Note
This project is for educational purposes. Always consult with a healthcare professional before starting any new fitness regimen.
