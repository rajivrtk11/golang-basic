package main

import (
	structsPerson "rajiv/struct"
)

func main() {
	// Create root command
	// var rootCmd = &cobra.Command{
	// 	Use:   "mycli",
	// 	Short: "MyCLI is a test CLI app built with Cobra",
	// 	Run: func(cmd *cobra.Command, args []string) {
	// 		fmt.Println("👋 Hello from Cobra CLI! Rajiv")
	// 	},
	// }

	// Add 'hello' command
	// var helloCmd = &cobra.Command{
	// 	Use:   "hello",
	// 	Short: "Prints a hello message",
	// 	Run: func(cmd *cobra.Command, args []string) {
	// 		fmt.Println("👋 Hello from Cobra CLI!")
	// 	},
	// }

	// Add 'hello' to root
	// rootCmd.AddCommand(helloCmd)
	// rootCmd.AddCommand(command.Command)
	// Execute root command
	// if err := rootCmd.Execute(); err != nil {
	// 	fmt.Println(err)
	// 	os.Exit(1)
	// }

	// Struct practice
	p := structsPerson.CreatePerson()
	p.Name = "Sachin kumar singh"
}
