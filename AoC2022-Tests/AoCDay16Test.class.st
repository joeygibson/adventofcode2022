Class {
	#name : 'AoCDay16Test',
	#superclass : 'AoCBaseTest',
	#category : 'AoC2022-Tests',
	#package : 'AoC2022-Tests'
}

{ #category : 'setup' }
AoCDay16Test >> setUp [
	super setUp.
	self day: '16'.
	self path: FileLocator home / 'Projects' / 'adventofcode2022' asString.

	inst := AoCDay16 new.
]

{ #category : 'executing' }
AoCDay16Test >> testPart1WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 1638.
		
]

{ #category : 'executing' }
AoCDay16Test >> testPart1WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part1: lines.

	self assert: res equals: 1651.
		
]

{ #category : 'executing' }
AoCDay16Test >> testPart2WithRealData [
|lines res|

	lines := self
		         readInputFileAsLines: 'input1.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 0.
		
]

{ #category : 'executing' }
AoCDay16Test >> testPart2WithSample [
|lines res|

	lines := self
		         readInputFileAsLines: 'input0.txt'
		         for: self day
		         onPath: self path.

	res := inst part2: lines.

	self assert: res equals: 0.
		
]
