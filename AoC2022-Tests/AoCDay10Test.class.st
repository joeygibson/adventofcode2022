Class {
	#name : 'AoCDay10Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'running' }
AoCDay10Test >> setUp [
	super setUp.
	self day: '10'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay10 new.
]

{ #category : 'tests' }
AoCDay10Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 12460.
]

{ #category : 'tests' }
AoCDay10Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input2.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 13140.
]
