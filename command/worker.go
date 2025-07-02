package command

import (
	"context"
	"fmt"
	"os"
	"os/signal"
	"time"

	"github.com/spf13/cobra"
)

var Command = &cobra.Command{
	Use:   "worker",
	Short: "Starts the background job worker",
	Run: func(cmd *cobra.Command, args []string) {
		fmt.Println("🚀 Worker started... Press Ctrl+C to stop")

		ctx, stop := signal.NotifyContext(context.Background(), os.Interrupt)
		defer stop()

		for {
			select {
			case <-ctx.Done():
				fmt.Println("\n🛑 Worker stopped gracefully")
				return
			default:
				processJob()
				time.Sleep(2 * time.Second)
			}
		}
	},
}

func processJob() {
	fmt.Println("🔧 Processing job at", time.Now().Format("15:04:05"))
	// Simulate some work here
}
