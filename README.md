# Job Title Normalization Program

This software is designed to normalize job titles using various techniques such as Jaro-Winkler similarity, keyword comparison, and memorization. It provides a simple user interface for performing job title normalization.

## Getting Started

To use this program, follow these steps:

1. **Run the Program:** Start the program to access the normalization functionality.
2. **Menu Options:**
    - **Normalize Job Title:** Enter a job title to normalize.
    - **Exit:** Quit the program.

## How it Works

1. **Normalization Process:** 
   - Enter a job title to normalize.
   - The program uses techniques like Jaro-Winkler similarity, keyword comparison, and memorization to find the most suitable normalized job title.
   - If multiple possibilities are found, the program prompts you to choose the most correct one.
   - The normalized job title is displayed, and it is also saved in memory for future reference.

2. **Usage Notes:** 
   - Ensure to input valid options when prompted.
   - Follow the on-screen instructions for smooth operation.

## Example Usage

```
Menu:

Normalize Job Title
Exit
Choose an option: 1
Enter job title: Java Developer
We found 3 possibilities that match Java Developer. Choose the most correct one:
Software Engineer
Java Programmer
Full Stack Developer
Choose an option: 1
Normalized Job Title: Software Engineer
##########################################################################################
Menu:

Normalize Job Title
Exit
Choose an option: 2
Exiting...
```
