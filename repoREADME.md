push 

Get-ChildItem -Recurse -Force -Filter ".git" -Directory | ForEach-Object { git -C $_.Parent.FullName add .; git -C $_.Parent.FullName commit -m "din commit besked"; git -C $_.Parent.FullName push }

pull 

Get-ChildItem -Recurse -Force -Filter ".git" -Directory | ForEach-Object { git -C $_.Parent.FullName pull }
