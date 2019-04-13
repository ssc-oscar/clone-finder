# Framework for finding code clones within huge amount of blobs

##Overview

This framework aims at finding file-level code clone in type 2 clone which allows different in white spaces, comments and identifier names.

Given a file in a particular language, find a list of relevant files that may be a clone of (or be cloned by) the given file. In addition, the result will provide a similarity measurement and in which project's which revision those files occur.



## Process Flow

### Build database for matching

First, we build a inverted index database for all blobs that represent a file in WoC. But we can't deal with the code just like dealing with nature language.

In the phase of text segmentation, the code file is split into code blocks instead of words.

Then in the phase of stemming, we remove the comments in each block, normalize the use of white space and line break, replace the identifier names with a specific word.

We also need the phase of removing stopped words. Some empty blocks or small blocks (less than 3 statements) should be removed.

Finally, we can calculate the SHA1 value of each blocks, then output a list of pair \<SHA1, start line num, end line num>. The inverted index database will build on those SHA1 of code block. 



Blob: 05fe634ca4c8386349ac519f899145c75fff4169

-> Fetch blob content, determine which language the blob belongs to, call the language specific segmentation engine.

-> \<code block 1, start line 1, end line 1>, ...,  \<code block n, start line n, end line n>

-> \<stemmed code block 1, start line 1, end line 1>, ...,  \<stemmed code block n, start line n, end line n>

-> \<code block SHA1 1, start line 1, end line 1>, ...,  \<code block SHA1 n, start line n, end line n>



After inverted index:

code block SHA1 x -> \<blob 1, start line x1, end line x1>, ....,  \<blob n, start line xn, end line xn>

code block SHA1 y -> \<blob 3, start line y3, end line y3>, ....,  \<blob m, start line ym, end line ym>

......

### Do matching

When matching a given file, we first split the file into a list of code block SHA1 using the same engine in database building step.

Then for each code block SHA1, we can fetch a list of  \<blob, start line num, end line num> pair from inverted index database.

We can count how many times (or lines) a blob is judged as a clone of the given file, then output the list of blob whose count greater than threshold. Also we should output the lines that are judged as a clone for each blob.

Finally, we can do any further process with the outputted blob and line number. Such as, find out which project and which revision the clone belong to by using other mapper in WoC. Or find out is there any relationship between outputted blob (maybe they belong to the commits that have a relation of parent) and we can find the source of the clone.