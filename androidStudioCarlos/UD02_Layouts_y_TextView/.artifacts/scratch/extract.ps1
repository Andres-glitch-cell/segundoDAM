$path = "UD02_A01.pdf"
if (Test-Path $path) {
    $bytes = [System.IO.File]::ReadAllBytes($path)
    $text = [System.Text.Encoding]::ASCII.GetString($bytes)
    $matches = [System.Text.RegularExpressions.Regex]::Matches($text, '\(([^)]+)\)')
    foreach ($m in $matches) {
        if ($m.Groups[1].Value.Length -gt 1) {
            $m.Groups[1].Value
        }
    }
} else {
    Write-Host "PDF not found"
}
