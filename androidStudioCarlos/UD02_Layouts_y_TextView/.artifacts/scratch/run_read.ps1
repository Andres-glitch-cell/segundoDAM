$py = Get-Command python -ErrorAction SilentlyContinue
if ($py) {
    python .artifacts/scratch/read_pdf.py
} else {
    Write-Host "Python not found in PATH"
}
