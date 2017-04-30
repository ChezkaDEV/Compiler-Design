# Intro

Name:				Chezka Eusebio

Instuctor:			Pitcher, Corin

Course:				CSC 378 - COMPLIER DESIGN

Language:   		Python

This repository is for CSC348 Winter 2017.

# Overview
An overview of the design of a compiler for a general purpose programming
language; tools for designing the components of the compiler; implementing
the compiler; run time environments



Download latest release: 
        
        Python 3.4.6 2017-01-17
        https://www.python.org/downloads/

References:
- http://www.lihaoyi.com/fastparse/

The Python Language Reference:
- https://docs.python.org/2/reference/
        
Official Python Grammar (Python 2.5) (updated 2/13/2011):

        https://inst.eecs.berkeley.edu/~cs164/fa11/python-grammar.html

# Notes

- My parser for python is white space sensitive. Meaning that the compiler will fail to parse it unless space is hardcoded in the parse.
- Files in this path are being tested for compilation. Which includes: count.py, divisible.py, fact.py, hello.py
        
        src/test/scala/hello.py
- Count.py: prints out numbers from 0-100. 
- Divisible.py: prints out numbers from 1-100 that are divisible by 3 and 5.
- Fact.py: prints out factorial from 1-10
- hello.py: prints "Hello World!"


==============================================================================
# README:

My compiler parses and compiles through terminal. But when I run it on IntelliJ (it runs and compiles on the IntelliJ Terminal), it gives me errors and warnings. It still passes though. I just wanted to let you know since this is something weird it does.

    C:\Users\Chezka\IdeaProjects\csc348-2017-2-eusebioc_complier\src\main\scala\csp\ch03\Project.scala
                Error:Error:line (5)Cannot resolve symbol fastparse
                Error:Error:line (5)Cannot resolve symbol fastparse
                Error:Error:line (40)Cannot resolve symbol fastparse
                Error:Error:line (40)Cannot resolve symbol fastparse
                Error:Error:line (68)Cannot resolve symbol fastparse
                Error:Error:line (68)Cannot resolve symbol fastparse
                Error:Error:line (70)Cannot resolve symbol Parser
                Error:Error:line (70)Cannot resolve symbol Parser
                Error:Error:line (70)Cannot resolve symbol P
                Warning:Warning:line (71)Suspicious forward reference in class
                Error:Error:line (71)Cannot resolve symbol |
                Warning:Warning:line (72)Suspicious forward reference in class
                Error:Error:line (72)Cannot resolve symbol ~
                Warning:Warning:line (72)Unnecessary parentheses
                Warning:Warning:line (72)Suspicious forward reference in class
                Error:Error:line (72)Cannot resolve symbol rep
                Error:Error:line (72)Cannot resolve symbol sep
                Error:Error:line (72)Missing parameter type: s
                Error:Error:line (72)Cannot resolve symbol toList
                Error:Error:line (74)Cannot resolve reference Call with such signature
                Error:Error:line (74)Type mismatch, expected: List[Project.Expr], actual: Any
                Error:Error:line (79)Cannot resolve symbol Parser
                Error:Error:line (79)Cannot resolve symbol Parser
                Error:Error:line (79)Cannot resolve symbol P
                Error:Error:line (80)Cannot resolve symbol ~
                Error:Error:line (80)Cannot resolve symbol |
                Error:Error:line (80)Cannot resolve symbol ~
                Error:Error:line (80)Missing parameter type: s
                Error:Error:line (80)Cannot resolve symbol toList
                Error:Error:line (80)Missing arguments for method foldAssocLeft((Project.Expr, List[(String, Project.Expr)]))
                Error:Error:line (80)Cannot resolve reference foldAssocLeft with such signature
                Error:Error:line (82)Cannot resolve symbol Parser
                Error:Error:line (82)Cannot resolve symbol Parser
                Error:Error:line (82)Cannot resolve symbol P
                Error:Error:line (83)Cannot resolve symbol ~
                Error:Error:line (83)Cannot resolve symbol |
                Error:Error:line (83)Cannot resolve symbol ~
                Error:Error:line (83)Missing parameter type: s
                Error:Error:line (83)Cannot resolve symbol toList
                Error:Error:line (83)Missing arguments for method foldAssocLeft((Project.Expr, List[(String, Project.Expr)]))
                Error:Error:line (83)Cannot resolve reference foldAssocLeft with such signature
                Error:Error:line (85)Cannot resolve symbol Parser
                Error:Error:line (85)Cannot resolve symbol Parser
                Error:Error:line (85)Cannot resolve symbol P
                Error:Error:line (86)Cannot resolve symbol ~
                Error:Error:line (86)Cannot resolve symbol |
                Error:Error:line (86)Cannot resolve symbol |
                Error:Error:line (86)Cannot resolve symbol |
                Error:Error:line (86)Cannot resolve symbol |
                Error:Error:line (86)Cannot resolve symbol ~
                Error:Error:line (86)Missing parameter type: s
                Error:Error:line (86)Cannot resolve symbol toList
                Error:Error:line (86)Missing arguments for method foldAssocLeft((Project.Expr, List[(String, Project.Expr)]))
                Error:Error:line (86)Cannot resolve reference foldAssocLeft with such signature
                Error:Error:line (88)Cannot resolve symbol Parser
                Error:Error:line (88)Cannot resolve symbol Parser
                Error:Error:line (88)Cannot resolve symbol P
                Error:Error:line (89)Cannot resolve symbol ~
                Error:Error:line (89)Cannot resolve symbol |
                Error:Error:line (89)Cannot resolve symbol |
                Error:Error:line (89)Cannot resolve symbol ~
                Error:Error:line (89)Missing parameter type: s
                Error:Error:line (89)Cannot resolve symbol toList
                Error:Error:line (89)Missing arguments for method foldAssocLeft((Project.Expr, List[(String, Project.Expr)]))
                Error:Error:line (89)Cannot resolve reference foldAssocLeft with such signature
                Error:Error:line (91)Cannot resolve symbol Parser
                Error:Error:line (91)Cannot resolve symbol Parser
                Error:Error:line (91)Cannot resolve symbol P
                Error:Error:line (93)Cannot resolve symbol Parser
                Error:Error:line (93)Cannot resolve symbol Parser
                Error:Error:line (93)Cannot resolve symbol P
                Warning:Warning:line (93)Suspicious forward reference in class
                Error:Error:line (93)Cannot resolve symbol ~
                Warning:Warning:line (93)Suspicious forward reference in class
                Error:Error:line (93)Cannot resolve symbol |
                Error:Error:line (93)Cannot resolve symbol CharIn
                Error:Error:line (93)Missing parameter type: s
                Warning:Warning:line (93)Suspicious forward reference in class
                Error:Error:line (94)Cannot resolve symbol Parser
                Error:Error:line (94)Cannot resolve symbol Parser
                Error:Error:line (94)Cannot resolve symbol P
                Error:Error:line (94)Cannot resolve symbol CharIn
                Error:Error:line (94)Cannot resolve symbol |
                Error:Error:line (94)Cannot resolve symbol CharIn
                Error:Error:line (96)Cannot resolve symbol Parser
                Error:Error:line (96)Cannot resolve symbol Parser
                Error:Error:line (96)Cannot resolve symbol P
                Error:Error:line (96)Cannot resolve symbol CharIn
                Error:Error:line (96)Cannot resolve symbol toInt
                Error:Error:line (97)Cannot resolve symbol Parser
                Error:Error:line (97)Cannot resolve symbol Parser
                Error:Error:line (97)Cannot resolve symbol P
                Error:Error:line (97)Cannot resolve symbol CharIn
                Error:Error:line (97)Missing parameter type: s
                Error:Error:line (97)Cannot resolve symbol toInt
                Error:Error:line (98)Cannot resolve symbol Parser
                Error:Error:line (98)Cannot resolve symbol Parser
                Error:Error:line (98)Cannot resolve symbol map
                Error:Error:line (98)Missing parameter type: n
                Error:Error:line (99)Cannot resolve symbol Parser
                Error:Error:line (99)Cannot resolve symbol Parser
                Error:Error:line (99)Cannot resolve symbol P
                Error:Error:line (99)Cannot resolve symbol map
                Error:Error:line (99)Missing parameter type: s
                Error:Error:line (101)Cannot resolve symbol Parser
                Error:Error:line (101)Cannot resolve symbol Parser
                Error:Error:line (101)Cannot resolve symbol P
                Error:Error:line (101)Cannot resolve symbol ~
                Error:Error:line (101)Cannot resolve symbol ~
                Error:Error:line (105)Cannot resolve symbol Parser
                Error:Error:line (105)Cannot resolve symbol Parser
                Error:Error:line (105)Cannot resolve symbol P
                Error:Error:line (106)Cannot resolve symbol Parser
                Error:Error:line (106)Cannot resolve symbol Parser
                Error:Error:line (106)Cannot resolve symbol P
                Error:Error:line (106)Cannot resolve symbol rep
                Error:Error:line (106)Cannot resolve symbol length
                Error:Error:line (109)Cannot resolve symbol Parser
                Error:Error:line (109)Cannot resolve symbol Parser
                Error:Error:line (109)Cannot resolve symbol ~
                Error:Error:line (109)Cannot resolve symbol flatMap
                Error:Error:line (109)Missing parameter type: i
                Warning:Warning:line (110)Suspicious forward reference in class
                Error:Error:line (110)Cannot resolve symbol rep
                Error:Error:line (110)Cannot resolve symbol sep
                Error:Error:line (110)Cannot resolve symbol ~/
                Error:Error:line (110)Missing parameter type: s
                Error:Error:line (110)Cannot resolve symbol toList
                Error:Error:line (113)Cannot resolve symbol Parser
                Error:Error:line (113)Cannot resolve symbol Parser
                Error:Error:line (113)Cannot resolve symbol P
                Error:Error:line (113)Cannot resolve symbol rep
                Error:Error:line (113)Cannot resolve symbol ~
                Error:Error:line (113)Cannot resolve symbol rep
                Error:Error:line (113)Cannot resolve symbol ~
                Warning:Warning:line (114)Suspicious forward reference in class
                Error:Error:line (114)Cannot resolve symbol |
                Error:Error:line (114)Cannot resolve symbol |
                Warning:Warning:line (115)Suspicious forward reference in class
                Error:Error:line (115)Cannot resolve symbol |
                Warning:Warning:line (115)Suspicious forward reference in class
                Error:Error:line (115)Cannot resolve symbol |
                Warning:Warning:line (115)Suspicious forward reference in class
                Error:Error:line (115)Cannot resolve symbol |
                Warning:Warning:line (115)Suspicious forward reference in class
                Error:Error:line (115)Cannot resolve symbol |
                Warning:Warning:line (115)Suspicious forward reference in class
                Error:Error:line (124)Cannot resolve symbol P
                Error:Error:line (124)Cannot resolve symbol P
                Error:Error:line (124)Cannot resolve symbol P
                Error:Error:line (124)Cannot resolve symbol ~
                Error:Error:line (124)Cannot resolve symbol ~
                Warning:Warning:line (124)Unnecessary parentheses
                Error:Error:line (124)Cannot resolve symbol ?
                Error:Error:line (124)Missing parameter type: e
                Error:Error:line (125)Cannot resolve symbol P
                Error:Error:line (125)Cannot resolve symbol P
                Error:Error:line (125)Cannot resolve symbol P
                Error:Error:line (125)Cannot resolve symbol ~
                Error:Error:line (125)Cannot resolve symbol map
                Error:Error:line (125)Missing parameter type: d
                Error:Error:line (126)Cannot resolve symbol P
                Error:Error:line (126)Cannot resolve symbol P
                Error:Error:line (126)Cannot resolve symbol P
                Error:Error:line (127)Cannot resolve symbol ~
                Warning:Warning:line (127)Unnecessary parentheses
                Error:Error:line (130)Cannot resolve symbol P
                Error:Error:line (130)Cannot resolve symbol P
                Error:Error:line (130)Cannot resolve symbol P
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (131)Cannot resolve symbol ~
                Error:Error:line (136)Cannot resolve symbol P
                Error:Error:line (136)Cannot resolve symbol P
                Error:Error:line (136)Cannot resolve symbol P
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol rep
                Error:Error:line (137)Cannot resolve symbol ~
                Error:Error:line (137)Cannot resolve symbol rep
                Error:Error:line (142)Cannot resolve symbol P
                Error:Error:line (142)Cannot resolve symbol P
                Error:Error:line (142)Cannot resolve symbol P
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol ?
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol rep
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol rep
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol rep
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol rep
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (143)Cannot resolve symbol ~
                Error:Error:line (148)Cannot resolve symbol P
                Error:Error:line (148)Cannot resolve symbol P
                Error:Error:line (148)Cannot resolve symbol P
                Error:Error:line (149)Cannot resolve symbol ~
                Error:Error:line (149)Cannot resolve symbol ~
                Error:Error:line (149)Cannot resolve symbol ~
                Error:Error:line (149)Cannot resolve symbol rep
                Error:Error:line (149)Cannot resolve symbol sep
                Error:Error:line (149)Missing parameter type: s
                Error:Error:line (149)Cannot resolve symbol toList
                Error:Error:line (149)Cannot resolve symbol ~
                Error:Error:line (149)Cannot resolve symbol ~
                Error:Error:line (153)Cannot resolve symbol P
                Error:Error:line (153)Cannot resolve symbol P
                Error:Error:line (153)Cannot resolve symbol P
                Error:Error:line (154)Cannot resolve symbol rep
                Error:Error:line (154)Missing parameter type: s
                Error:Error:line (154)Cannot resolve symbol toList
                Error:Error:line (154)Cannot resolve symbol ~
                Error:Error:line (154)Cannot resolve symbol ~
                Error:Error:line (158)Cannot resolve symbol Parser
                Error:Error:line (158)Cannot resolve symbol Parser
                Error:Error:line (158)Cannot resolve symbol P
                Error:Error:line (158)Cannot resolve symbol ~
                Error:Error:line (158)Cannot resolve symbol End
                Error:Error:line (158)Cannot resolve symbol End
                Warning:Warning:line (193)Remove redundant braces
                Warning:Warning:line (200)Remove redundant braces
                Warning:Warning:line (213)Remove redundant braces
                Warning:Warning:line (216)Suspicious shadowing by a Variable Pattern
                Warning:Warning:line (216)Remove redundant braces
                Warning:Warning:line (219)Suspicious shadowing by a Variable Pattern
                Error:Error:line (239)Cannot resolve symbol fastparse
                Error:Error:line (239)Cannot resolve symbol fastparse
                Error:Error:line (239)Cannot resolve symbol Parsed
                Error:Error:line (239)Cannot resolve symbol Parsed
                Error:Error:line (239)Cannot resolve symbol Parser
                Error:Error:line (239)Cannot resolve symbol Parser
                Warning:Warning:line (247)Remove braces from import statement containing only one import
                Warning:Warning:line (250)Unnecessary parentheses
                Warning:Warning:line (256)Unnecessary parentheses
                Warning:Warning:line (264)Java mutator method accessed as parameterless
                Error:Error:line (270)Cannot resolve symbol Parser
                Error:Error:line (270)Cannot resolve symbol Parser
                Error:Error:line (271)Cannot resolve symbol fastparse
                Error:Error:line (271)Cannot resolve symbol fastparse
                Error:Error:line (271)Cannot resolve symbol parse
                Error:Error:line (273)Cannot resolve symbol Parsed
                Error:Error:line (273)Cannot resolve symbol Parsed
                Warning:Warning:line (273)Remove redundant braces
                Warning:Warning:line (274)Malformed format specifier
                Warning:Warning:line (274)Malformed format specifier
                Error:Error:line (276)Cannot resolve symbol Parsed
                Error:Error:line (276)Cannot resolve symbol Parsed
                Warning:Warning:line (276)Remove redundant braces
                Warning:Warning:line (277)Malformed format specifier
                Warning:Warning:line (277)Malformed format specifier
                Warning:Warning:line (277)Malformed format specifier
                Error:Error:line (283)Cannot resolve symbol Parser
                Error:Error:line (283)Cannot resolve symbol Parser
                Error:Error:line (285)Cannot resolve symbol fastparse
                Error:Error:line (285)Cannot resolve symbol fastparse
                Error:Error:line (285)Cannot resolve symbol parse
                Error:Error:line (287)Cannot resolve symbol Parsed
                Error:Error:line (287)Cannot resolve symbol Parsed
                Warning:Warning:line (287)Remove redundant braces
                Warning:Warning:line (288)Malformed format specifier
                Warning:Warning:line (288)Malformed format specifier
                Error:Error:line (290)Cannot resolve symbol Parsed
                Error:Error:line (290)Cannot resolve symbol Parsed
                Warning:Warning:line (290)Remove redundant braces
                Warning:Warning:line (291)Malformed format specifier
                Warning:Warning:line (291)Malformed format specifier
                Warning:Warning:line (291)Malformed format specifier
                Warning:Warning:line (321)Remove redundant braces
                Warning:Warning:line (330)Remove redundant braces
                Warning:Warning:line (336)Remove redundant braces
                Warning:Warning:line (353)Remove redundant braces
                Warning:Warning:line (382)No format specifer for an argument nm1
                Warning:Warning:line (416)Remove redundant braces
                Warning:Warning:line (430)No argument for a format specifier %s
                Warning:Warning:line (442)Remove redundant braces
                Warning:Warning:line (451)Suspicious shadowing by a Variable Pattern
                Warning:Warning:line (451)Remove redundant braces
                Warning:Warning:line (471)Suspicious shadowing by a Variable Pattern
                Warning:Warning:line (471)Remove redundant braces
                Warning:Warning:line (484)Remove redundant braces
                Warning:Warning:line (492)Remove redundant braces
                Warning:Warning:line (499)Remove redundant braces
                Warning:Warning:line (521)Remove redundant braces
                Warning:Warning:line (530)Suspicious shadowing by a Variable Pattern
                Warning:Warning:line (530)Remove redundant braces
                Warning:Warning:line (533)Suspicious shadowing by a Variable Pattern
                Warning:Warning:line (533)Remove redundant braces
                Warning:Warning:line (536)Remove redundant braces
                Warning:Warning:line (539)Remove redundant braces
                Warning:Warning:line (542)Remove redundant braces
                Warning:Warning:line (549)Replace conversion to Set and back with .distinct