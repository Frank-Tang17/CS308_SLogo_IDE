### API CHANGES

#### Overview

As a group, there has been one monumental change that we made to the general design of our project that later modified our APIs. Specifically, early on in our coding,
our group decided to eliminate the Controller classes. This was because we decided to use bindings to update the information in the view such that any change in the backend would automatically reflect in the frontend.

Aside from the implementation of binding, our general design has stayed relatively the same. The backend APIs have certainly changed more then the frontend, specifically due to the existence of so many public
methods in the ConcreteCommands package.
 
My work has been dedicated totally to the Frontend Internal section of our project. I'm glad that there have not been *too* many changes my API here. I document the changes that do exist below, though.

Before doing so, I want to note that at first, I was hesitant to add binding to our project, since I did not want to change our APIs so drastically. However, after thinking through it more, I believe that change itself is not inherently bad—so long as it benefits our project.
Binding eased many aspects of our work, and for that reason, while it did change our APIs, I believe that it was worth the switch.

With that said, Property Binding was *not* my idea. It was the backend team's desire to implement it. At first I was hesitant. But now I agree with them that these were positive changes.

#### Frontend Internal

The Frontend Internal changed mostly due to the implementation of binding. This was not in our original plan; however, once we learned more about the concept, we realized that it would simplify our project significantly.

Specifically, the following methods have been affected by this change:

``` java
public void updateBackgroundColor(Color color);
public void updatePenColor();
public void updateTurtleImage();
public void updateTurtle();
```

None of these methods are still public, since the updates are used via binding. As such, there is no need to tell the View to update this information; instead, the Bindings updates automatically. These changes are major, but they are beneficial and purposeful.

Additionally, we are no longer calling the following methods:
``` java
public void displayTurtle();
public void displayGrid();
public void displayInfo();
```

Instead, we use a variety of getter methods that return a Node to be added to a scene. displayInfo() for example, is now replaced by adding the node of an InfoView class to the display. This allows us to to create the visualization by adding various components to the scene in SlogoView, centralizing the display in one class (while also using composition). This change is also better, since the app is centralized well in one location now.
