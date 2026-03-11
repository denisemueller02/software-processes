# Script to convert README.md to PDF using Pandoc

# Check if tools are provided via environment (from Gradle build)
$pandocDir = $env:PANDOC_DIR
$wkhtmltopdfDir = $env:WKHTMLTOPDF_DIR

# Try to use Gradle-downloaded tools first
if ($pandocDir -and (Test-Path $pandocDir)) {
    $pandocExe = Get-ChildItem -Path $pandocDir -Filter "pandoc.exe" -Recurse | Select-Object -First 1
    if ($pandocExe) {
        Write-Host "Using Pandoc from: $($pandocExe.FullName)" -ForegroundColor Green
        $pandocPath = $pandocExe.FullName
    }
} 

# Fall back to system Pandoc if Gradle version not available
if (-not $pandocPath) {
    $pandocPath = (Get-Command pandoc -ErrorAction SilentlyContinue).Source
}

if (-not $pandocPath) {
    Write-Host "Pandoc is not installed. Use 'gradle preparePdfTools' to download it automatically." -ForegroundColor Red
    Write-Host ""
    Write-Host "Or install manually:" -ForegroundColor Yellow
    Write-Host "  Download from: https://github.com/jgm/pandoc/releases/latest" -ForegroundColor Yellow
    Write-Host "  Using Chocolatey (admin): choco install pandoc" -ForegroundColor Yellow
    Write-Host ""
    exit 1
}

# Check if a PDF engine is installed (required by Pandoc)
$pdfEngineFound = $false
$pdfEngine = $null

# Check for wkhtmltopdf in Gradle-downloaded location
if ($wkhtmltopdfDir -and (Test-Path "$wkhtmltopdfDir\wkhtmltopdf.exe")) {
    $pdfEngineFound = $true
    $pdfEngine = "wkhtmltopdf (bundled)"
    $env:PATH = "$wkhtmltopdfDir;$env:PATH"
}
# Check for MiKTeX (pdflatex)
elseif ($null -ne (Get-Command pdflatex -ErrorAction SilentlyContinue)) {
    $pdfEngineFound = $true
    $pdfEngine = "MiKTeX"
}
# Check for TeX Live (xetex)
elseif ($null -ne (Get-Command xetex -ErrorAction SilentlyContinue)) {
    $pdfEngineFound = $true
    $pdfEngine = "TeX Live"
}
# Check for wkhtmltopdf in system PATH
elseif ($null -ne (Get-Command wkhtmltopdf -ErrorAction SilentlyContinue)) {
    $pdfEngineFound = $true
    $pdfEngine = "wkhtmltopdf (system)"
}

if (-not $pdfEngineFound) {
    Write-Host "PDF engine not found! Pandoc needs a PDF engine to convert to PDF." -ForegroundColor Red
    Write-Host ""
    Write-Host "Options:" -ForegroundColor Yellow
    Write-Host "  1. Run 'gradle preparePdfTools' - downloads wkhtmltopdf automatically" -ForegroundColor Green
    Write-Host "  2. Install MiKTeX: choco install miktex" -ForegroundColor Yellow
    Write-Host "  3. Install wkhtmltopdf: choco install wkhtmltopdf" -ForegroundColor Yellow
    Write-Host ""
    exit 1
} else {
    Write-Host "Found PDF engine: $pdfEngine" -ForegroundColor Green
}

# Define paths
$readmePath = ".\README.md"
$outputPath = ".\README.pdf"

if (-not (Test-Path $readmePath)) {
    Write-Host "Error: README.md not found!" -ForegroundColor Red
    exit
}

# Convert Markdown to PDF
Write-Host "Converting README.md to PDF..." -ForegroundColor Green
& "$pandocPath" "$readmePath" -o "$outputPath" --from markdown --to pdf

if ($LASTEXITCODE -eq 0) {
    Write-Host "Success! PDF created at: $outputPath" -ForegroundColor Green
    # Open the PDF
    & "$outputPath"
} else {
    Write-Host "Error: Conversion failed!" -ForegroundColor Red
    exit 1
}
