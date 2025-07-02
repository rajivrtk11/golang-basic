package main

import "fmt"

type Animals struct {
	name string
	age  int
}

func (a *Animals) PrintHello() {
	a.name = "rajiv"
	fmt.Println("the value is ", a)
	a.sleep()
}

// func main() {
// 	cat := Animals{"Billi", 112}
// 	fmt.Println("the cat age is ", cat)

// 	cat.PrintHello()
// 	fmt.Println("the cat age is ", cat)
// 	println("Hello world")
// }
