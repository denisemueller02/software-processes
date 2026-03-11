@echo off
REM Script to convert README.md to PDF using Pandoc

REM Check if Pandoc is installed
where pandoc >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Pandoc is not installed. Please install it using one of these methods:
    echo.
    echo 1. Manual Download (Recommended for non-admin):
    echo    Download from: https://github.com/jgm/pandoc/releases/latest
    echo    Download the Windows MSI installer and run it
    echo.
    echo 2. Using Chocolatey (requires admin):
    echo    choco install pandoc
    echo.
    echo 3. Using Scoop:
    echo    scoop install pandoc
    echo.
    pause
    exit /b 1
)

REM Convert Markdown to PDF
echo Converting README.md to PDF...
pandoc "README.md" -o "README.pdf" --from markdown --to pdf

if %ERRORLEVEL% EQU 0 (
    echo Success! PDF created at: README.pdf
    start README.pdf
) else (
    echo Error: Conversion failed!
    pause
    exit /b 1
)
