# 1. Clean and build project
Window: gradlew clean build
Linux: ./gradlew clean build
Mac: gradle clean build

# 2. Run project
java -jar ./build/libs/order-1.0.0.jar <input-folder>

Example: java -jar ./build/libs/order-1.0.0.jar /Users/vutg/Documents/personal/order/test_files
        Then check the output files in folder /Users/vutg/Documents/personal/order/test_files/output

# 3. What to improve
Build the rest api to support other services to easily call to this service
Improve the command line: input, output