package models

import "fmt"

func RunLoop() {
	var number = [4]int{1, 2, 3, 4}
	for i := 0; i < len(number); i++ {
		fmt.Println("the num is ", number[i])
	}
}
