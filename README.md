# software-processes

## Commands

### Building the Project
```bash
./gradlew build
```
Note: Might need to be adjusted depending on where your gradle wrapper is!

### Running the Application
Start directly via Main (run java at top)

### Generating PDF from Markdown

The PDF is generated when the Workflows are executed. You can manually generate it by executing the Docker command from the workflow (Step generate PDF from README) in your project root. Make sure Docker is running!

## Workflows

There are 3 Workflows: development, main and release. These three are almost identical, with the only difference being, that the main branch also generates a jacoco report and sends it to our SonarQube instance.

### General
The workflows All have two jobs, build and deploy. 
Build first generates the PDF from the readme, then builds and pushes the image to our deployment server.
Then, the Deploy Job first stops the container image of the previous build if it is running, and then starts the image of the new build. 

### Main
In the main branch, the Build Job contains an additional step called "SonarQube Scan" this grabs the generated Jacoco report, which is always generated upon build (in all branches, it is configured build.gradle.kts) and sends it to our SonarQube instance. 