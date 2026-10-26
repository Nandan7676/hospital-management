$ErrorActionPreference = 'Stop'

# Local PostgreSQL settings. Change DB_PASSWORD if your postgres role uses another password.
$env:DB_URL = 'jdbc:postgresql://localhost:5432/meddibuddy'
$env:DB_USER = 'postgres'
$env:DB_PASSWORD = 'test'
$env:PGPASSWORD = $env:DB_PASSWORD
$env:JWT_SECRET = 'meddibuddy-local-development-secret-change-before-deployment-2026'
$env:SPRING_PROFILES_ACTIVE = 'postgres'

# Seed this local administrator if it does not already exist.
$env:APP_ADMIN_EMAIL = 'admin@meddibuddy.local'
$env:APP_ADMIN_PASSWORD = 'Admin@MeddiBuddy2026'

# Find PostgreSQL client tools in PATH or a standard Windows installation folder.
$psqlCommand = Get-Command psql.exe -ErrorAction SilentlyContinue
$psql = if ($psqlCommand) { $psqlCommand.Source } else { $null }
if (-not $psql) {
    $psql = Get-ChildItem -Path "$env:ProgramFiles\PostgreSQL\*\bin\psql.exe" -ErrorAction SilentlyContinue |
        Select-Object -First 1 -ExpandProperty FullName
}
if (-not $psql) {
    throw 'PostgreSQL command-line tools were not found. Install PostgreSQL and add its bin folder to PATH.'
}

# Create the project database only when it is missing.
$databaseExists = & $psql -h localhost -p 5432 -U postgres -d postgres -tAc "SELECT 1 FROM pg_database WHERE datname = 'meddibuddy'"
if ($LASTEXITCODE -ne 0) {
    throw 'Could not connect to PostgreSQL. Make sure its service is running and the postgres password is test.'
}
if ("$databaseExists".Trim() -ne '1') {
    $createdb = Join-Path (Split-Path $psql) 'createdb.exe'
    if (-not (Test-Path $createdb)) { throw 'createdb.exe was not found beside psql.exe.' }
    & $createdb -h localhost -p 5432 -U postgres meddibuddy
    if ($LASTEXITCODE -ne 0) { throw 'Could not create the meddibuddy database.' }
    Write-Host 'Created PostgreSQL database meddibuddy.' -ForegroundColor Green
} else {
    Write-Host 'PostgreSQL database meddibuddy already exists.' -ForegroundColor Green
}

Remove-Item Env:PGPASSWORD -ErrorAction SilentlyContinue
Set-Location $PSScriptRoot
mvn spring-boot:run
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
