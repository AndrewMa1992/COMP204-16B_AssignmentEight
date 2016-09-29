COMP204-16B / COMP242-16B Assignment 8
======================================

**Alpha** should be complete on **Wednesday, 5^(th) October at mid-day (12:00)**.

**Beta** and individual reports are due on **Wednesday, 12^(th) October at 23:30**.


Android Group Project
=====================

The goal of this exercise is to demonstrate your Android skills learned in 
previous weeks, and to gain experience working in a team utilizing version control.

Android is described in the [documentation supplied by Google, found here](https://developer.android.com/index.html).


Preamble
========

1. Watch the [Git for Collaboration (Multiplayer Git)](http://coursecast.its.waikato.ac.nz/Panopto/Pages/Viewer.aspx?id=dcddcc13-7650-411b-957d-6d1ae249b033) screencast on Panopto
  * Follow the process described in the screencast to configure the repository that has been set up for your group.
2. After the initial setup and adding of your project, you should not push directly to the master repository
  * Avoid merging in code that breaks master. You should aim to have it so that you can always compile and run the code from master
3. Follow the branching process each time you want to work on a new item
  * One feature per branch!
4. Remember you can have many branches at the same time, you can work on multiple features simultaneously across different branches
5. Your life will be made a lot easier if you all use the same version of Android Studio
  * Pick a version and stick with it. Ignore any prompts to update for the duration of this assignment


Overview
========

You will be building an application that utilizes all the concepts you have learned about
Android development and object structures. In addition you will experiment
with software engineering techniques, and utilize git and GitLab features for collaboration.
You will develop your application in two stages - alpha and beta

Task
====

* Read the whole specification. All the way through. Seriously. Read it twice.
* Meet with your group to discuss your project plan
* Read the specification again. Together.
* Using the issue tracker, create an issue that describes all the required setup work for your project
  * Read what is required in [the application](#the-application) subsection of this document
  * Choose your API level. Aim for a version that will work with the most up-to-date device you have available
  * Choose a project name
  * What things do you know your project will need
* Create a new Android Project project in your repository. Refer to the [Multiplayer Git](http://coursecast.its.waikato.ac.nz/Panopto/Pages/Viewer.aspx?id=dcddcc13-7650-411b-957d-6d1ae249b033) screencast.
  * Use your created issue to guide your setup
* Begin creating issues describing tasks that need to be done for your ```alpha``` release
  * Consider using a milestone to group these, and labels to describe them
  * Provide detail, link to other issue that block/depend on each issue
  * Set due dates and assign some tasks straight away
* Fork the repository and begin working, as described in 
the [Multiplayer Git](http://coursecast.its.waikato.ac.nz/Panopto/Pages/Viewer.aspx?id=dcddcc13-7650-411b-957d-6d1ae249b033) screencast
  * Assign your merge requests to another member of your team, refer to the [Merge Request subsection](#merge-requests)
* Begin adding issues related to your ```beta``` release
  * As with the alpha release tasks, consider using a milestone to group these
  * Add detail and assign/schedule as appropriate
* Begin working on beta issues
  * Continue to add issues and make merge requests until complete


The Application
---------------

You need to create an application that consists of a ball game with supporting 'activities' (in the Android sense).

The game should:
* Contain a ball that interacts with an environment
* Demonstrate good object structure.
* Utilize **both** accelerometer and touch sensors
  * What you do with them is up to you, however you must use both
* Be engaging
  * You will get marks for it being interesting/fun, so be inventive!

Along with the game you must provide 2-3 additional activities of your choice. 
Examples of potential activities are a high-score screen, a home screen, an options screen; but you are not limited to these.
At least one of your implemented activities must send data to another (eg, passing a score to a high-score screen, passing a difficult argument to the game).
**Groups consisting of 4 people must implement 3 activities (total of 4 including the game)**.

The application will be marked in 2 parts, **alpha** and **beta**. 

The **alpha** release should consist of:
* All of the activities for the application demonstrating any linkages between them
* Placeholder content

Your **alpha** release will be marked on the afternoon of Wednesday, 5^(th) October. 
No code submission will be necessary, as we will be marking it directly off of GitLab. 

The **beta** release should consist of:
* The completed application with placeholder content replaced/removed

Your **beta** release will be marked in the R-block labs, with dates and times to be scheduled at a later date. 
No code submission will be necessary, as we will be marking it directly off of GitLab. 
Group members should remember to submit their [individual reports](#individual-reports) on moodle.


Issue Tracker
-------------

* The issue tracker is a useful tool for tracking work items, and for recording progress
* We expect to see that the issue tracker has been used
  * We should see tasks/issues that have been commented on, tracking progress and relationships to other issues
* Issues should generally be quite granular (relating to one work item)
  * If an issue gets too complicated, create new tasks that are more suitably sized and close the original issue
* Usage of the issue tracker will be considered as part of your collaboration marks


Merge Requests
--------------

* Merge requests should be assigned to someone other than the creator
* The assignee should have a look at the content of the request before accepting it
  * This can be evidenced by a comment on the request indicating that it is ok, and/or some form of code review
* Acknowledgements of merge requests in either form will be considered as part of your collaboration marks 


Individual Reports
------------------

Each group member must write a report addressing the following points with 1-2 paragraphs of text for each point.

* Explain how using git has affected your personal workflow
* Do you feel that using the collaborative git workflow as demonstrated helped with this assignment? Why, or why not?
* What assistive tools or features could have helped with the project?


Submission
==========

Project will be demonstrated in-person, with details to be released at a later date.

Individual reports must be [submitted to moodle](https://elearn.waikato.ac.nz/mod/assign/view.php?id=574764) in markdown format by 23:30 on Wednesday, 12^(th) October.


Grading
=======

| Weighting | Allocated to |
|:---------:|--------------|
| 10% | Engaging gameplay |
| 15% | Individual Report |
| 25% | Collaboration |
| 25% | Object Structure |
| 25% | Meeting Specifications |
