Class {
	#name : 'AoCDay08Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay08Test >> setUp [
	super setUp.
	self day: '8'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay08 new.
]

{ #category : 'tests' }
AoCDay08Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 1820.
]

{ #category : 'tests' }
AoCDay08Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 21.
]

{ #category : 'tests' }
AoCDay08Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 385112.
]

{ #category : 'tests' }
AoCDay08Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 8.
]
