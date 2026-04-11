push 

git -C C:\ITU add .; git -C C:\ITU commit -m "din commit besked"; git -C C:\ITU push; Get-ChildItem -Recurse -Force -Filter ".git" -Directory | ForEach-Object { git -C $_.Parent.FullName add .; git -C $_.Parent.FullName commit -m "din commit besked"; git -C $_.Parent.FullName push }

pull 

git -C C:\ITU pull; Get-ChildItem -Recurse -Force -Filter ".git" -Directory | ForEach-Object { git -C $_.Parent.FullName pull }
