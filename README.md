# software-processes

## Building the Project
```bash
./gradlew build
```

## Running the Application
Start directly via Main (run java at top)

## Generating PDF from Markdown

### Option 1: Using Batch Script (Windows)
Double-click `generate-pdf.bat` in the project root

### Option 2: Using PowerShell Script
```powershell
.\generate-pdf.ps1
```

### Option 3: Manual Command
First, install Pandoc:
- **Download**: https://github.com/jgm/pandoc/releases/latest
- **Or use Scoop**: `scoop install pandoc`
- **Or use Chocolatey**: `choco install pandoc` (requires admin)

Then run:
```bash
pandoc README.md -o README.pdf
```

This will create a `README.pdf` file in the project root directory.

