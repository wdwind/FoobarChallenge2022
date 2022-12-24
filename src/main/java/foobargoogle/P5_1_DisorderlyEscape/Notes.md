Obviously this is a pure math problem. And I (and probably most of the people) was not aware of it before. 
So the first question is how to get start?

Hint: calculate the first few numbers in the sequence, and search them in [OEIS](https://oeis.org/).

For example, for n*n grid with 2 states/colors, the first few numbers in the sequence are `1, 2, 7`:

* Grid 0*0 has 0 configurations/orbits
* Grid 1*1 has 2 configurations/orbits (0 and 1 / white and black)
* Grid 2*2 has 7 configurations/orbits as given by the problem

Searching `1, 2, 7 "matrices"` in OEIS gives https://oeis.org/search?q=1%2C+2%2C+7+%22matrices%22&sort=&language=&go=Search .
Although the one we are looking for (A002724) is not the first result, but clearly it indicates the problem
is something related to matrix and permutation. It is a good direction to further dig in.

It turns out this is about some famous theories in group theory, specifically Burnside's lemma, and Pólya enumeration theorem.
Related resources:

* https://math.stackexchange.com/questions/22159/how-many-n-times-m-binary-matrices-are-there-up-to-row-and-column-permutation
* https://en.wikipedia.org/wiki/Burnside%27s_lemma
* https://en.wikipedia.org/wiki/P%C3%B3lya_enumeration_theorem
* https://math.stackexchange.com/questions/2056708/number-of-equivalence-classes-of-w-times-h-matrices-under-switching-rows-and
* https://math.stackexchange.com/questions/4151046/a-group-orbit-burnsides-lemma-question
* https://en.wikipedia.org/wiki/Cycle_index#Symmetric_group_Sn
* https://mathworld.wolfram.com/SymmetricGroup.html
* https://math.stackexchange.com/questions/2164412/cycle-index-of-symmetric-group-sanity-check

* https://zh.m.wikipedia.org/zh/%E4%BC%AF%E6%81%A9%E8%B5%9B%E5%BE%B7%E5%BC%95%E7%90%86
* https://zhuanlan.zhihu.com/p/80261375
* https://zhuanlan.zhihu.com/p/218379827
* https://zhuanlan.zhihu.com/p/268777559
* http://jianhang.work/page/6/
* http://jianhang.work/2015/11/26/2015/11/boliyajishudingli34/
