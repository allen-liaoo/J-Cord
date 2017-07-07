# Contribution
You are welcome to contribute to *J-Cord*. Please go ahead and fork the project, then start
submitting issues or pull requests.

## Issue
If you came across any questions, bugs, or typos in this repository, you are welcome to submit an issue to address it.
Feel free to attach graphs and screenshots to help stating the issue.


## Pull Request
Please feel free and pull requests! If you have any enhancements or new ideas to the repository,
just submit a pull request! Here are some stuff to take note of:
1. Please add Javadocs and inline documentations. Javadocs are required for any java files that
 are not in the `internal` package. Inline documentations may only be presented to a class, abstract class,
 enumeration, or a default method in an interface.
2. If part of the pull request is not finished, please add a todo `// TODO: [Todo stuff here]` stating
 the current status.
3. Coding style: Tabs should be 4 spaces long. The bracket style should follow
 [1 TBS](https://en.wikipedia.org/wiki/Indent_style#Variant:_1TBS_.28OTBS.29), which the opening brackets
 are located at the end of a line.
4. All objects (not internal) should have getters and setters.
5. Javadocs: All parameters and exceptions requires documentation. Please use `@exception` if the
 exception extends `RuntimeException`, and use `@throws` if the exception is checked exception.
 Always leave one blank line between the paragraph explaining what this methods do and the parameters.
 The order of documentations are as follows: <br />
   1. Brief Description of the Method (Please do not make it too long)
   2. Blank Line
   3. Unchecked Exceptions using (`@exception`)
   4. Check Exceptions (`@throws`)
   5. Parameters (`@param`)
   6. Return value (`@return`) <br />
 You are welcome to pull requests that help enforce this javadoc style.

## Good luck!
Now get out there and do your best! <br />
Make sure to watch the project for further updates! <br />
Ps. Your great work will always be credited on the commit message.