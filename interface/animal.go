package main

import "fmt"

// Define an interface
type Animal interface {
	Speak()
	Sleep()
}

// Define a struct
type Dog struct{}

// Implement the Speak method for Dog (implicitly satisfies Animal interface)
func (d Dog) Speak() {
	fmt.Println("Woof!")
}

func (d Dog) Sleep() {
	fmt.Println("Dog is sleeping !")
}

// func main() {
// 	var myDog Animal = Dog{} // Assign struct to interface
// 	myDog.Speak()            // Output: Woof!
// 	myDog.Sleep()
// }
