package models

import "fmt"

func RunMap() {
	ages := map[string]int{"sahil": 12, "rajiv": 14}

	// add new element into the map
	ages["raman"] = 15

	// delete key from age
	delete(ages, "sahil")

	// check if key exist in map
	value, exists := ages["sahil"]

	if exists {
		fmt.Println("Sahil does not exist in map ", value)
	}

	fmt.Println("The age map is ", ages)
}
