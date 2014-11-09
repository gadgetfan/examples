cd %1
call git status
pause
call git checkout master
call git checkout -b %2
call git checkout %2
call git rebase master
cd ..
rem pause