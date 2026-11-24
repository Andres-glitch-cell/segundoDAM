try {
    Add-Type -AssemblyName System.Runtime.WindowsRuntime
    $pdfFile = Get-Item "UD02_A01.pdf"
    $asyncOp = [Windows.Data.Pdf.PdfDocument]::LoadFromFileAsync($pdfFile)
    $pdfDoc = $asyncOp.AsTask().Result
    Write-Host "Page count: $($pdfDoc.PageCount)"

    $folder = Get-Location
    for ($i = 0; $i -lt $pdfDoc.PageCount; $i++) {
        $page = $pdfDoc.GetPage($i)
        $stream = New-Object Windows.Storage.Streams.InMemoryRandomAccessStream
        $page.RenderToStreamAsync($stream).AsTask().Wait()

        $decoder = [Windows.Graphics.Imaging.BitmapDecoder]::CreateAsync($stream).AsTask().Result
        $softwareBitmap = $decoder.GetSoftwareBitmapAsync().AsTask().Result

        $width = $softwareBitmap.PixelWidth
        $height = $softwareBitmap.PixelHeight

        $format = [Windows.Graphics.Imaging.BitmapPixelFormat]::Bgra8
        $alpha = [Windows.Graphics.Imaging.BitmapAlphaMode]::Premultiplied
        $transBitmap = [Windows.Graphics.Imaging.SoftwareBitmap]::Convert($softwareBitmap, $format, $alpha)

        $bytes = New-Object byte[] ($width * $height * 4)
        $transBitmap.CopyToBuffer($bytes)

        Add-Type -AssemblyName System.Drawing
        $bmp = New-Object System.Drawing.Bitmap $width, $height, [System.Drawing.Imaging.PixelFormat]::Format32bppArgb
        $rect = New-Object System.Drawing.Rectangle 0, 0, $width, $height
        $bmpData = $bmp.LockBits($rect, [System.Drawing.Imaging.ImageLockMode]::WriteOnly, $bmp.PixelFormat)
        [System.Runtime.InteropServices.Marshal]::Copy($bytes, 0, $bmpData.Scan0, $bytes.Length)
        $bmp.UnlockBits($bmpData)

        $outPath = Join-Path $folder.Path "page_$($i+1).png"
        $bmp.Save($outPath, [System.Drawing.Imaging.ImageFormat]::Png)
        Write-Host "Saved $outPath"
        $bmp.Dispose()
    }
} catch {
    Write-Host "Error: $_"
}
