package structsPerson

import "fmt"

type Person struct {
	Name string
	age  int
	job  string
}

func CreatePerson() (p Person) {
	person := Person{"Rajiv singh", 12, "Software Engineer"}
	fmt.Println("The person is ", person)
	return person
}

func (p *Person) GetAge() (q *Person) {
	return p
}
