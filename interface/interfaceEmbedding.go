package main

import "fmt"

func (a *Animals) sleep() {
	fmt.Println("The dog is sleeping ", a)
}

func main() {
	dog := Animals{"tara", 12}
	fmt.Println("The animal is ", dog)

	dog.PrintHello()
	dog.sleep()
	fmt.Println("hello world")
}
