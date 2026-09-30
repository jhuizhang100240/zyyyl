<#
读取根目录 .env 并启动后端。Spring Boot 不会自动加载 .env，这里逐行注入进程环境变量。
用法：powershell -ExecutionPolicy Bypass -File scripts/run-local.ps1 [-JarPath <path>]
#>
param(
    [string]$JarPath = "zyyyl-admin/target/zyyyl-admin.jar"
)

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent $PSScriptRoot
$envFile = Join-Path $root '.env'
if (-not (Test-Path $envFile)) {
    throw "未找到 .env 文件：$envFile"
}

foreach ($line in Get-Content $envFile -Encoding UTF8) {
    $trimmed = $line.Trim()
    if ($trimmed.Length -eq 0 -or $trimmed.StartsWith('#')) {
        continue
    }
    $index = $trimmed.IndexOf('=')
    if ($index -le 0) {
        continue
    }
    $name = $trimmed.Substring(0, $index).Trim()
    $value = $trimmed.Substring($index + 1).Trim()
    Set-Item -Path "Env:$name" -Value $value
}

$jar = Join-Path $root $JarPath
if (-not (Test-Path $jar)) {
    throw "未找到可执行 JAR：$jar，请先执行 mvn -DskipTests package"
}

$env:JAVA_HOME = if ($env:JAVA_HOME) { $env:JAVA_HOME } else { 'C:\Program Files\Java\jdk-21' }
$java = Join-Path $env:JAVA_HOME 'bin/java.exe'
Write-Host "启动后端：$jar"
& $java -jar $jar
