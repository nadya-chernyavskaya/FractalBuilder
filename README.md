## Build a fractal of your dreams

An easy to use application to build and study fractals. The application builds a Mandelbrot set from any user-defined complex function. Once the fractal (Mandelbrot set) is build, you can infinitely zoom-in to explore its structure. You can also choose a point on the fractal to build a Julia set from. Users can choose among different color-schemes and save all created images. 

### Introduction

- Find out more about fractals, Mandelbrot and Julia sets [here](https://en.wikipedia.org/wiki/Mandelbrot_set).
- This application has a very basic interface as it was built as a simple university project in 2014. The interfance of the application is currently provided in both English and Russian. For Russian use the branch 'rus', for English use the branch 'eng'.

### Application
<img src="https://github.com/chernyavskaya/FractalBuilder/blob/rus/screenshots/example_main_frame.png" width="600">

Example of a Julia set generator from a fractal 

<img src="https://github.com/chernyavskaya/FractalBuilder/blob/rus/screenshots/example_julia.png" width="600">


### Installation Instuctions

 - Git clone the repository 
 - Compile the Java source code. You can do so either from your IDE or from a command line using the Java compiler that comes with the Java SDK. If you have never used Java before, you can download Java SDK from [here](https://www.oracle.com/java/technologies/downloads/) and then run the two commands below to compile launch the application.
 - To compile with Java SDK do the following :
    - Go the root directory of the project 
    - Run ```javac src/*/*java -d classes```
 - Once the code is compiled you can launch the application. If you use an IDE, you can launch it directly from there. Otherwise, run the following command ```java -cp classes main_interface.MainFrame```

##### LICENSE

See the LICENSE file for license rights and limitations (MIT).
