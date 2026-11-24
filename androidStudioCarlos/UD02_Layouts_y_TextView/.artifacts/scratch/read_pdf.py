import pypdf
reader = pypdf.PdfReader('UD02_A01.pdf')
for i, page in enumerate(reader.pages):
    print(f"--- PAGE {i+1} ---")
    print(page.extract_text())
