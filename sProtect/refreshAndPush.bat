rem call lastChangesAndCheckout %1 %2
cd %1
call git status
pause
call git checkout master
call git rebase %2
pause
cd ..
